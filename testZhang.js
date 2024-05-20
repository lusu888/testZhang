// Crea un'applicazione Node.js che:
//- legga un file da un path inserito in input dall’utente (può essere un path locale o un indirizzo web)
//- il numero totale di parole nel file
//- il numero di lettere nel file
//- il numero di spazi nel file
//- le parole che si ripetono più di 10 volte e indicare il numero di volte in cui si ripete.
//
// Authour: Zhang
// update date: 20-May-2024
const fs= require('fs')
const readline = require('readline')

const rl = readline.createInterface({
    input:process.stdin,
    output: process.stdout
})

rl.question("Input file path",(filePath) => {
    fs.readFile(filePath, 'utf8', (err, data) => {
	   if(err){
		  console.errir('error file: ',err);
		  return;
	   }

	   const words = data.split(/\s+/);
	   const wordCount = words.length;
	   const letterCount = data.replace(/\s+/g, '').length;

	   const spaceCount = data.split(' ').length - 1;//space

	   const wordMap = {};
	   words.forEach((word) => {
	   wordMap[word] = (wordMap[word] || 0) + 1;
	   });
	   const repeatedWords = Object.keys(wordMap).filter((word) => wordMap[word] > 10);

	   console.log('Total number of words:', wordCount);
	   console.log('Total number of letters:', letterCount);
	   console.log('Total number of spaces:', spaceCount);
	   console.log('Words repeated more:');
	   repeatedWords.forEach((word) => {
		console.log(`${word}: ${wordMap[word]} times`);
	   });

	   rl.close();
    })
})


