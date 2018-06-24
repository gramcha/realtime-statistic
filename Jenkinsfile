
node {
	def app
	def mavendocker
    stage('Clone repository') {
        /* Let's make sure we have the repository cloned to our workspace */

        checkout scm
    }
    stage('Prerequisite') {
    		mavendocker =docker.image('maven:3.3.3')
    		sh 'echo "maven ready"'
    }
    stage('Build') {
        /* This builds the actual image; synonymous to
         * docker build on the command line */
         mavendocker.inside {
            sh 'mvn install -DskipTests'
        }
        app = docker.build("realtime-statistic")
    }
    stage('Test image') {
			mavendocker.inside {
				sh 'mvn test'
			}
			junit 'target/surefire-reports/*.xml'
    }
    stage('Publish to Dockerhub'){
    		docker.withRegistry('https://registry.hub.docker.com', 'Dockerhub') {
            app.push("${env.BUILD_NUMBER}")
            app.push("latest")
        }                   
 	}

}
