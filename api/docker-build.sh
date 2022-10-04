./mvnw package
docker build -t nagarciah/tinypim .
echo ""
echo "################################################################"
echo "  Docker image built. Run with:"
echo "    docker run --rm -d -p 8080:8080 nagarciah/tinypim"
echo "################################################################"
echo ""
