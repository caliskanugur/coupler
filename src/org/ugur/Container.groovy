class Container {

    public String ResultsOut = 'results.xml'
    public String ResultsJSON = 'results.json'
    public String EnvPath = '.env'

    String Name
    String Image

    String ContainerRootPath
    private String testsumBinPath

    Container(String name, String image) {
        this.Name = name
        this.Image = image

    if image.Contains('go') {
        this.ContainerRootPath = '/root/go/src/github.com/rancher/tests/validation/'
        this.testsumBinPath = '/root/go/bin/gotestsum'
    }

    }

    void Build(buildPath = './validation/build.sh', configurePath = './validation/configure.sh') {
        echo 'Configuring and building test container'

        sh buildPath
        sh configurePath
    }

    void RunGoTest(String packagePath, List<String> tags, String testCase, String timeout = '60m') {
        sh "docker run --name ${this.Name} -t --env-file ${this.EnvPath} ${this.Image} sh -c ${this.testSumBinPath} --format standard-verbose --packages=${packagePath} --junitfile ${container.ResultsOut} --jsonfile ${container.ResultsOutJSON} -- -tags=${tags} ${testCase} -timeout=${timeout} -v;"
    }

    void Remove() {
        echo "Removing container ${this.Name}"

        sh "docker stop ${this.Name}"
        sh "docker rm -v ${this.Name}"
        sh "docker rmi -f ${this.Image}"
    }

}
