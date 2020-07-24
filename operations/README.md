## Create Python virtual environment
```
python3 -m venv venv
source venv/bin/activate
pip3 install -r requirements.txt
```

```
aws iam list-account-aliases
```

## Execute for a development environment:
```
cd operations
ansible-playbook -i inventories/dev/hosts cicd/trigger/pr/pr-trigger.yml
```



## Setup CentOS 8 WSL 2 on Windows 10
1. https://www.how2shout.com/how-to/how-to-install-centos-8-on-wsl-windows-10.html
2. dnf install python3
3. pip3 install --upgrade pip
4. pip3 install ansible
5. pip3 install boto3
6. https://docs.aws.amazon.com/cli/latest/userguide/install-cliv2-linux.html
7. aws configure
8. dnf install groff-base
9. dnf install openssh-clients