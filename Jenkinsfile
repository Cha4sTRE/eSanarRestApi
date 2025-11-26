pipeline {
    agent any

    environment {
        IMAGE_NAME = "j3ffer/enfsanar-api"
        TAG = "latest"
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'master', url: 'https://github.com/Cha4sTRE/eSanarRestApi.git'
            }
        }

        stage('Test & Build JAR') {
            steps {
                sh "./mvnw clean install"
            }
        }

        stage('Build Docker Image') {
            steps {
                sh "docker build -t ${IMAGE_NAME}:${BUILD_NUMBER} ."
                sh "docker tag ${IMAGE_NAME}:${BUILD_NUMBER} ${IMAGE_NAME}:${TAG}"
            }
        }

        stage('Push to DockerHub') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'docker-id',
                    usernameVariable: 'USER',
                    passwordVariable: 'PASS'
                )]) {
                    sh "echo $PASS | docker login -u $USER --password-stdin"
                    sh "docker push ${IMAGE_NAME}:${TAG}"
                }
            }
        }
        stage('Deploy to CapRover') {
            steps {
                withCredentials([string(credentialsId: 'captain-id', variable: 'captain-id')]) {
                    sh '''
                        caprover deploy \
                        --default \
                        --caproverUrl https://sanar-api.projects.20022004.xyz \
                        --caproverApp sanar-api \
                        --imageName j3ffer/esanar-api:latest \
                        --appToken $captain-id
                    '''
                }
            }
        }

    }
}
