#command to launch the game
for param in "$*"
do
  java -cp build/main/:. prs.Main $param
done
