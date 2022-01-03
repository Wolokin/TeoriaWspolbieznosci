
function printAsync(s, cb) {
   var delay = Math.floor((Math.random()*1000)+500);
   setTimeout(function() {
       console.log(s);
       if (cb) cb();
   }, delay);
}

// Napisz funkcje (bez korzytania z biblioteki async) wykonujaca 
// rownolegle funkcje znajdujace sie w tablicy 
// parallel_functions. Po zakonczeniu wszystkich funkcji
// uruchamia sie funkcja final_function. Wskazowka:  zastosowc 
// licznik zliczajacy wywolania funkcji rownoleglych 

var glob;
function wait(final_function, cb) {
    if(glob !== 0) {
        setTimeout(function () {
            wait(final_function, cb);
        }, 5);
        return;
    }
    final_function();
}
function inparallel(parallel_functions, final_function) {
    glob = parallel_functions.length;
    for(let i = 0; i < parallel_functions.length; i++) {
        setTimeout(
            function() {
                parallel_functions[i](function() {glob--;});
            }, 0);
    }
    wait(final_function);
}

A=function(cb){printAsync("A",cb);}
B=function(cb){printAsync("B",cb);}
C=function(cb){printAsync("C",cb);}
D=function(cb){printAsync("Done",cb);}

inparallel([A,B,C],D) 

// kolejnosc: A, B, C - dowolna, na koncu zawsze "Done" 
