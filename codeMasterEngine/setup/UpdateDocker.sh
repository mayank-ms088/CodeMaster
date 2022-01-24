echo "Creating Docker Image"
docker build -t codemaster_vm .
echo "Retrieving Installed Docker Images"
docker images