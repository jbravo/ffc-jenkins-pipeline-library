def defraUtils

def mergedPrNo = ''
def pr = ''
def version = ''
def serviceName = 'ffc-jenkins-pipeline-library'
def versionFileName = "VERSION"

node {
  checkout scm

  try {
    stage('Load defraUtils functions') {
      defraUtils = load 'src/uk/gov/defra/ffc/DefraUtils.groovy'
    }
    stage('Set GitHub status as pending'){
      defraUtils.setGithubStatusPending()
    }
    stage('Set PR and version variables') {
      (pr, containerTag, mergedPrNo) = defraUtils.getVariables(serviceName, defraUtils.getFileVersion(versionFileName))
      version = defraUtils.getFileVersion(versionFileName)
    }
    if (pr != '') {
      stage('Verify version incremented') {
        defraUtils.verifyFileVersionIncremented(versionFileName)
      }
    }
    else {
      stage('Trigger GitHub release') {
        withCredentials([
          string(credentialsId: 'github-auth-token', variable: 'gitToken')
        ]) {
          def releaseSuccess = defraUtils.triggerRelease(version, serviceName, version, gitToken)

          if (releaseSuccess) {
            defraUtils.addSemverTags(version, serviceName)
          }
        }
      }
    }
    stage('Set GitHub status as success'){
      defraUtils.setGithubStatusSuccess()
    }
  } catch(e) {
    defraUtils.setGithubStatusFailure(e.message)
    defraUtils.notifySlackBuildFailure(e.message, "#generalbuildfailures")
    throw e
  }
}
