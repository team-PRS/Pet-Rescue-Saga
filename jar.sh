#create a jar file.
#exemple of use : ./jar.sh PRS
cd build/main/.;jar cfm $1.jar ../../manifest.txt *;mv $1.jar ../../.;cd ../..
