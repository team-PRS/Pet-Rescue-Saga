./javac.sh
./jar.sh PRS
mkdir PRS
mv PRS.jar PRS/.
cp gamers.bin PRS/.
cp -r data/ PRS/.
zip -qr PRS.zip PRS/
rm -fr PRS
