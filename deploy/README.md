# Ephemeral/Local Deployment Folder

This folder is used by the CI to deploy your application in a Kubernetes ephemeral environement. Just put the yaml files required to deploy your applications.

We use a simple `kubectl apply -f deploy/` to start an ephemeral environment. That mean you can deploy your application with:

- Plain yaml
- Helm (using HelmOperator resource)

## Requirements

Even if you use plain yaml or helm, you must provide at least the resources below:

- 1 ingressRoute (traefik ingresscontroller)
- 1 certificat (certmanager) (using `cname-letsencrypt-prod` issuer)
- 1 service
- 1 deployment or statefulset :)

Note:

- Don't forget to create an ingress using host `DUMMY_ADDRESS_TO_CHANGE.eph.member.decathlon.net` (we will *sed* it with the project-name/pr-number)

- You can modify the step `Set kubernetes variables` in [feature_branch.yml](.github/workflows/feature_branch.yml) depending of the packaging you use (helm or plain yaml)
