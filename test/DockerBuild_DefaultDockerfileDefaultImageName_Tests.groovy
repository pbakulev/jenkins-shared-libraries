import TestData.Docker.DockerBuildTestData
import Utils.Helper
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized.class)
class DockerBuild_DefaultDockerfileDefaultImageName_Tests extends GroovyTestCase {

    @Parameterized.Parameters(name = "{0}")
    static Collection<Object[]> data() {
        DockerBuildTestData.suite_DefaultDockerFilePathsWithDefaultImageNames()
    }

    protected String path
    protected String imageName
    protected dockerBuild_ = new dockerBuild()

	DockerBuild_DefaultDockerfileDefaultImageName_Tests(List list){
        this.path = list[0]
        this.imageName = list[1]
    }

    @Before
    void setUp(){
        def variables = DockerBuildTestData.commonVariables()
        Helper.setEnvVariables(variables, dockerBuild_)
        InjectVars.injectTo(dockerBuild_, 'imageName', 'imageTag', 'commitId')
    }

    @Test
    void test_DockerBuild_shellCommandDockerBuildIsExecutedWithDefaultPath(){
        def actualCommands = []
        dockerBuild_.sh = { command ->
            if (command instanceof Map){
                if (command.returnStdout && command.script == "git log -n 1 --pretty=format:'%H'"){
                    return "111111122222222222222222"
                }
            }
            actualCommands << command; return null
        }
        def expectedCommands = [
                'docker build . -f \"./Dockerfile\" -t \"registry.com/bilderlings/Job_Name:master-1\" -t \"registry.com/bilderlings/Job_Name:111111122222222222222222\" -t \"registry.com/bilderlings/Job_Name:1111111\"'
        ]

        dockerBuild_(path, imageName)

        assertEquals(expectedCommands, actualCommands)

    }

}
