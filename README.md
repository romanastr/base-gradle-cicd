# Base Gradle CI/CD project
A sample set of applications for a mythical business unit to demonstrate
modern application development and DevOps practices.
 
## Applications
The set of business applications revolves around retrieving data from external APIs and relaying
it to customers, collecting API usage, and presenting basic API usage aggregates. 
The external API of choice is [XKCD](https://xkcd.com/json.html). This unrestricted simple API
is perfect for a sample application. 
The application modules are:
* api - external API, which calls public XKCD API and relays data
* data-model - common objects utilized by components
* flyway - creation of database schema for usage reporting
* perf-tests-api - Gatling performance tests for `api` and `stats-api` 
* stats-api - external API, which returns aggregate `api` usage stats
* stats-reporting - reporting tool, which saves raw `api` usage into the database of choice.

## Monorepo
Several applications are chosen to reside in a [monorepo](https://en.wikipedia.org/wiki/Monorepo) 
not be  confused with a [monolithic application](https://en.wikipedia.org/wiki/Monolithic_application).
Monorepo facilitates easy collaboration between teams and integration, atomic commits, code refactoring.
 
## Build tools
Common build aren't always geared the most to support monorepos.
Building tens to thousands of projects residing in a common codebase from scratch + running
all the test would take a prohibitively long time. Incremental build strategies come to the rescue.
[Apache Maven](https://en.wikipedia.org/wiki/Apache_Maven), the most popular build tool for the 
backend applications, has only rudimentary incremental build capabilities. Specialized build tools 
like [Bazel](https://en.wikipedia.org/wiki/Bazel_(software)) allows for fast incremental builds
on a large scale using distributed build cache. However, IDE support and overall interoperability
with test and deployment tools might [lack behind](https://plugins.jetbrains.com/plugin/8609-bazel)
for such specialized tools, which are not open-sourced.
In turn, [Gradle](https://en.wikipedia.org/wiki/Gradle) is a good bet for supporting the monorepo.
Incremental build capabilities of Gradle including incremental test execution are advancing
with each version. Gradle now supports [distributed HTTP build cache](https://docs.gradle.org/current/userguide/build_cache.html#sec:build_cache_configure_remote),
which allows to effectively [compete with Bazel](https://blog.gradle.org/gradle-vs-bazel-jvm#:~:text=They%20both%20perform%20well%20in,incremental%20change%20to%20the%20monolith) 
in incremental build performance.

## Deployment
Deployment with Kubernetes is chosen to allow for great versatility and ability to deploy on 
various on-prem or cloud platforms. Pain points from the earlier days of Kubernetes gave way
to [wide adoption with substantial community support](https://stackoverflow.blog/2020/05/29/why-kubernetes-getting-so-popular/).
One of pain point of Kubernetes, non-trivial cluster administration with the control plane, can
be effectively delegated to a cloud platform of choice (e.g., on AWS) or be encapsulated
into a large solution like OpenShift. Cloud-native deployment on Kubernetes is our bet for 
a portable, reproducible, and scalable deployment. 
We adopt [Elastic Kubernetes Service (EKS)](https://aws.amazon.com/eks/) on AWS for this project.
 
## DevOps strategy
The overall DevOps strategy includes 
* AWS native build / deploy tools CodeBuild and CodePipeline,
* AWS deployment orchestration with CloudFormation,
* application deployment into AWS EKS driven by Kubernetes templates, 
* consolidation of infrastructure as code concept with Ansible tool.

AWS Cloud Development Kit and its extension for Kubernetes provide a 
[potential new alternative](https://aws.amazon.com/blogs/containers/introducing-cdk-for-kubernetes/)
to the above practices.
