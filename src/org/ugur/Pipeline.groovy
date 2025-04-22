class Pipeline {

    List<Container> containers = []
    String

    String validationPath = './validation'
    String sshPath = './validation/.ssh'

    String branch = env.BRANCH ? env.BRANCH : 'main'
    String repository = env.REPO ? [[url: "${env.REPO}"]] : scm.userRemoteConfig

    void Checkout() {
        checkout([
            $class: 'GitSCM',
            branches: [[name: "*/${branch}"]],
            extensions: scm.extensions + [[$class: 'CleanCheckout']],
            userRemoteConfigs: repo
      ])
    }

    void WriteSSHFiles() {
        if (env.AWS_SSH_PEM_KEY && env.AWS_SSH_KEY_NAME) {
            dir(sshPath) {
                def decoded = new String(AWS_SSH_PEM_KEY.decodeBase64())
                writeFile file: AWS_SSH_KEY_NAME, text: decoded
            }
        }

        dir(sshPath) {
            def decodedRsa = new String(AWS_SSH_RSA_KEY.decodeBase64())
            writeFile file: JENKINS_RKE_VALIDATION, text: decodedRsa
        }

        dir(validationPath) {
            def filename = 'config.yaml'
            def configContents = env.CONFIG

            writeFile file: filename, text: configContents
            env.CATTLE_TEST_CONFIG = pipeline.RootPath + filename
        }
    }

    void AddContainer(Container container) {
        containers << container
    }

    void CopyTestResults() {
        sh "docker cp ${this.Name}:${pipeline.RootPath}${this.ResultsOut} ."
        step([$class: 'JUnitResultArchiver', testResults: "**/${container.ResultsOut}"])
    }

    void PublishReports() {
        sh "${pipeline.RootPath}/pipeline/scripts/build_qase_reporter.sh"
        sh "${pipeline.RootPath}/reporter"
    }

}
