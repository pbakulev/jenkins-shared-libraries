import TestData.CommitIdTestData
import TestData.Docker.DockerPushLatestTestData
import TestData.Docker.DockerTestData
import Utils.Helper
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized.class)
class DockerPushLatest_DefaultImageNameTests extends GroovyTestCase {

    @Parameterized.Parameters(name = "{0}")
    static Collection<Object> data() {
        DockerTestData.defaultImageNames()
    }

    protected String imageName
    protected dockerPushLatest_ = new dockerPushLatest()

    DockerPushLatest_DefaultImageNameTests(String imageName){
        this.imageName = imageName
    }

    @Before
    void setUp(){
        def variables = DockerPushLatestTestData.commonVariables()
        Helper.setEnvVariables(variables, dockerPushLatest_)
        InjectVars.injectTo(dockerPushLatest_, 'imageName', 'imageTag', 'commitId')
    }

    @Test
    void test_DockerPushLatest_checkOrderOfCommands(){
        def actualShellCommands = []
        dockerPushLatest_.sh = { command ->
            if (command instanceof Map) {
                return CommitIdTestData.lastCommitIdClosure(command)
            }
            actualShellCommands << command
        }
        def expectedShellCommands = [
                'docker tag \"registry.com/bilderlings/Job_Name:1111111\" \"registry.com/bilderlings/Job_Name:latest\"',
                'docker push \"registry.com/bilderlings/Job_Name:latest\"',
        ]

        dockerPushLatest_(imageName)

        assertEquals(expectedShellCommands, actualShellCommands)

    }

}
