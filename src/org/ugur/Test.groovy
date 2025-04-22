class Test {

    Container container
    String command
    String outputPath = 'test_results'

    Test(Container container) {
        this.container = container
    }

    void setCommand(String command) {
        this.command = command
    }

    void setOutputPath(String outputPath) {
        this.outputPath = outputPath
    }

    void runTest() {
        println "Running tests in container ${container.name} using command: $command"
        // Simulate test execution (replace with actual test runner invocation)
        // For example, if it's a JUnit test:
        // def process = new ProcessBuilder("docker", "exec", container.name, "/bin/sh", "-c", "cd /app && ./gradlew test").start()
        // process.waitFor()
        // if (process.exitValue() != 0) {
        //    println "Tests failed"
        // }

        // Simulate test execution and results
        sleep(2000) // Simulate test execution time
        println 'Tests completed.'

        //Create a dummy test results directory with files
        def outputDir = new File(outputPath)
        if (!outputDir.exists()) {
            outputDir.mkdirs()
        }

        // Create dummy test result files
        new File(outputDir, 'test_result_1.txt').write('Test 1 passed')
        new File(outputDir, 'test_result_2.txt').write('Test 2 failed')
        new File(outputDir, 'test_result_3.txt').write('Test 3 skipped')
        println "Test results written to $outputPath"
    }

}
