def call(Map container) {
    sh "docker run --name ${container.Name} -t --env-file ${container.Env} ${container.Image} sh -c \"/root/go/bin/gotestsum --format standard-verbose --packages=${container.TestPackage} --junitfile ${container.ResultsOut} --jsonfile ${container.ResultsOutJSON} -- -tags=${container.Tags} ${container.TestCase} -timeout=${container.Timeout} -v;"
}
