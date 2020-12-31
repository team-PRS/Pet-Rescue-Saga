#command to launch the game
for param in "$*"
do
  java -cp build/main/:. prs.Jeu $param
done
