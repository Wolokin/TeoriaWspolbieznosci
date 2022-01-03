let waterfall = require('async/waterfall');

function printAsync(s, cb) {
   var delay = Math.floor((Math.random()*1000)+500);
   setTimeout(function() {
       console.log(s);
       if (cb) cb();
   }, delay);
}

function task1(cb) {
    printAsync("1", function() {
        task2(cb);
    });
}

function task2(cb) {
    printAsync("2", function() {
        task3(cb);
    });
}

function task3(cb) {
    printAsync("3", cb);
}

// wywolanie sekwencji zadan
// task1(function() {
//    console.log('done!');
// });

const makeRepeated = (arr, repeats) =>
    Array.from({ length: repeats }, () => arr).flat();

function loop(n) {
    arr = makeRepeated([task1], n);
    waterfall(
        arr, function (err, result) {
            console.log('done!');
        }
    );
}

/* 
** Zadanie:
** Napisz funkcje loop(n), ktora powoduje wykonanie powyzszej 
** sekwencji zadan n razy. Czyli: 1 2 3 1 2 3 1 2 3 ... done
** 
*/

loop(4);
