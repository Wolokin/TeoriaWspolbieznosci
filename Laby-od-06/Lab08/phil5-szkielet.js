var ForkTime = 0;
var ConductorTime = 0;


var Fork = function() {
    this.state = 0;
    return this;
}

Fork.prototype.acquire = function(cb, beb=1) { 
    // zaimplementuj funkcje acquire, tak by korzystala z algorytmu BEB
    // (http://pl.wikipedia.org/wiki/Binary_Exponential_Backoff), tzn:
    // 1. przed pierwsza proba podniesienia widelca Filozof odczekuje 1ms
    // 2. gdy proba jest nieudana, zwieksza czas oczekiwania dwukrotnie
    //    i ponawia probe itd.
    let self = this;
    setTimeout(function () {
        if(self.state === 0) {
            self.state = 1;
            ForkTime += 2*beb - 1;
            if(cb) cb();
        } else {
            self.acquire(cb, 2*beb);
        }
    }, beb);
}

Fork.prototype.release = function() {
    this.state = 0;
}

var Philosopher = function(id, forks) {
    this.id = id;
    this.forks = forks;
    this.f1 = id % forks.length;
    this.f2 = (id+1) % forks.length;
    return this;
}

Philosopher.prototype.startNaive = function(count) {
    var forks = this.forks,
        f1 = this.f1,
        f2 = this.f2,
        id = this.id;


    // zaimplementuj rozwiazanie naiwne
    // kazdy filozof powinien 'count' razy wykonywac cykl
    // podnoszenia widelcow -- jedzenia -- zwalniania widelcow
    let self = this;
    if(count === 0) return;
    forks[f1].acquire(function () {
        console.log("phil " + id + " took fork " + f1);
        forks[f2].acquire(function () {
            console.log("phil " + id + " is eating");
            forks[f1].release();
            forks[f2].release();
            self.startNaive(count - 1);
        });
    });
}

Philosopher.prototype.startAsym = function(count) {
    var forks = this.forks,
        f1 = this.f1,
        f2 = this.f2,
        id = this.id;
    
    // zaimplementuj rozwiazanie asymetryczne
    // kazdy filozof powinien 'count' razy wykonywac cykl
    // podnoszenia widelcow -- jedzenia -- zwalniania widelcow
    let self = this;
    if(count === 0) {
        console.log('Asym '+N+' '+ForkTime+' '+ConductorTime)
        return;
    }
    if(id % 2 === 0) [f1,f2] = [f2,f1];
    forks[f1].acquire(function () {
        // console.log("phil " + id + " took fork " + f1);
        forks[f2].acquire(function () {
            // console.log("phil " + id + " is eating");
            forks[f1].release();
            forks[f2].release();
            self.startAsym(count - 1);
        });
    });
}

var Conductor = function (initial_count) {
    this.count = initial_count;
    return this;
}

Conductor.prototype.acquire = function(cb, beb = 1) {
    let self = this;
    setTimeout(function () {
        if(self.count > 0) {
            self.count -= 1;
            ConductorTime += 2*beb -1;
            if(cb) cb();
        } else {
            self.acquire(cb, 2*beb);
        }
    }, beb);
}

Conductor.prototype.release = function (cb) {
    this.count += 1;
}

Philosopher.prototype.startConductor = function(count, conductor) {
    var forks = this.forks,
        f1 = this.f1,
        f2 = this.f2,
        id = this.id;
    
    // zaimplementuj rozwiazanie z kelnerem
    // kazdy filozof powinien 'count' razy wykonywac cykl
    // podnoszenia widelcow -- jedzenia -- zwalniania widelcow
    let self = this;
    if(count === 0) {
        console.log('Conductor '+N+' '+ForkTime+' '+ConductorTime)
        return;
    }
    conductor.acquire(function () {
        forks[f1].acquire(function () {
            // console.log("phil " + id + " took fork " + f1);
            forks[f2].acquire(function () {
                // console.log("phil " + id + " took fork " + f2);
                // console.log("phil " + id + " is eating");
                forks[f1].release();
                forks[f2].release();
                conductor.release();
                self.startConductor(count - 1, conductor);
            });
        });
    });
}

var N = 5;
var forks = [];
var philosophers = []
for (var i = 0; i < N; i++) {
    forks.push(new Fork());
}

for (var i = 0; i < N; i++) {
    philosophers.push(new Philosopher(i, forks));
}

conductor = new Conductor(N-1);
for (var i = 0; i < N; i++) {
    // philosophers[i].startNaive(10);
    // philosophers[i].startAsym(10);
    philosophers[i].startConductor(10, conductor);
}



