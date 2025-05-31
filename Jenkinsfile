pipeline{
    agent any

    environment{
        DOCKER_IMAGE = 'dockerapicalendario'
        CONTAINE_NAME = 'dockerapicalendario'
        DOCKER_NETWORK = 'dockerfestivos_red'
        DOCKER_BUILD_DIR = 'presentacion'
        HOST_PORT = '9085'
        CONTAINER_PORT = '8081'
    }

    stages{
        stage('Generar ejecutable de Maven'){
            steps {
                bat 'mvn clean package -DskipTests'
            }
        }

        stage('Construir imagen'){
            steps{
                dir("${DOCKER_BUILD_DIR}"){
                    bat "docker build -t ${DOCKER_IMAGE} ."
                }
            }
        }

        stage('Limpiar contenedor existente'){
            steps{
                script{
                    catchError(buildResult: 'SUCCESS', stageResult: 'UNSTABLE'){
                        bat """
                        docker container inspect ${CONTAINE_NAME} > nul 2>&1 &&(
                            docker container stop d${CONTAINE_NAME}
                            docker container rm ${CONTAINE_NAME}
                            ) || echo "No existe el contenedor '${CONTAINE_NAME}'"
                        """
                    }
                }
            }
        }

        stage('Desplegar el contenedor'){
            steps{
                script{
                    bat "docker container run --network ${DOCKER_NETWORK } --name ${CONTAINE_NAME} -p ${HOST_PORT}:${CONTAINER_PORT} -d ${DOCKER_IMAGE}"
                }
                
            }
        }
            
    }
}