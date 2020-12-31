#command to launch a test.
#exemple of use : ./javaj.sh prs.map.CompteTest
for param in "$@"
do
  echo ==$param==
  java -cp junit-4.13.1.jar:.:build/main:build/test junit.textui.TestRunner $param
done
