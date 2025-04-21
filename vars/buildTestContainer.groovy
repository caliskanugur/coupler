/* build the test container */

def call(Map params) {
    echo 'Configuring and building test container'

    if (env.AWS_SSH_PEM_KEY && env.AWS_SSH_KEY_NAME) {
        dir('./validation/.ssh') {
            def decoded = new String(AWS_SSH_PEM_KEY.decodeBase64())
            writeFile file: AWS_SSH_KEY_NAME, text: decoded
        }
    }

    dir('./validation/.ssh') {
        def decodedRsa = new String(AWS_SSH_RSA_KEY.decodeBase64())
        writeFile file: JENKINS_RKE_VALIDATION, text: decodedRsa
    }

    dir('./validation') {
        def filename = 'config.yaml'
        def configContents = env.CONFIG

        writeFile file: filename, text: configContents
        env.CATTLE_TEST_CONFIG = rootPath + filename
    }

    sh './validation/configure.sh'
    sh './validation/build.sh'
}
