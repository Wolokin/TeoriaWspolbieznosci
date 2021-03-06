var forkSum = 0;
var conductorSum = 0;

var Fork = function() {
    this.state = 0;
    return this;
}

Fork.prototype.acquire = function(cb) {
    var tryAfter = timeout => () => {
        if (this.state == 0) {
            this.state = 1;
            if (cb) cb();
        }
        else {
            console.log(`reacquiring in ${timeout} ms`);
            forkSum += timeout * 2;
            setTimeout(tryAfter(timeout * 2), timeout * 2);
        }
    };

    forkSum += 1;
    setTimeout(tryAfter(1), 1);
}

Fork.prototype.release = function() { 
    this.state = 0; 
}

var Conductor = function(initial_state) {
    this.state = initial_state;
    return this;
}

Conductor.prototype.acquire = function(cb) {
    var tryAfter = timeout => () => {
        if (this.state > 0) {
            this.state -= 1;
            if (cb) cb();
        }
        else {
            console.log(`reacquiring in ${timeout} ms`);
            conductorSum += timeout * 2;
            setTimeout(tryAfter(timeout * 2), timeout * 2);
        }
    };

    conductorSum += 1;
    setTimeout(tryAfter(1), 1);
}

Conductor.prototype.release = function(cb) {
    this.state += 1;
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

    var cycle = cycles_left => {
        forks[f1].acquire(() => {
            forks[f2].acquire(() => {
                console.log("nomnomnom");

                setTimeout(() => {
                    forks[f2].release();
                    forks[f1].release();

                    if (cycles_left > 0) {
                        cycle(cycles_left - 1);
                    }
                })
            })
        })
    };

    cycle(count);
}

Philosopher.prototype.startAsym = function(count) {
    var forks = this.forks,
        f1 = this.f1,
        f2 = this.f2,
        id = this.id;

    var cycle = cycles_left => {
        forks[id % 2 == 0 ? f1 : f2].acquire(() => {
            forks[id % 2 == 0 ? f2 : f1].acquire(() => {
                console.log("nomnomnom");

                setTimeout(() => {
                    forks[f2].release();
                    forks[f1].release();

                    if (cycles_left > 0) {
                        cycle(cycles_left - 1);
                    }
                    else {
                        console.log(`fork sum: ${forkSum}`)
                    }
                })
            })
        })
    };

    cycle(count);
}

Philosopher.prototype.startConductor = function(count, conductor) {
    var forks = this.forks,
        f1 = this.f1,
        f2 = this.f2,
        id = this.id;

    var cycle = cycles_left => {
        conductor.acquire(() => {
            forks[f1].acquire(() => {
                forks[f2].acquire(() => {
                    console.log("nomnomnom");
    
                    setTimeout(() => {
                        forks[f2].release();
                        forks[f1].release();
                        conductor.release();
    
                        if (cycles_left > 0) {
                            cycle(cycles_left - 1);
                        }
                        else {
                            console.log(`conductor sum: ${conductorSum}`)
                        }
                    })
                })
            })
        })
    };

    cycle(count);
}


var N = 15;
var forks = [];
var philosophers = []

for (var i = 0; i < N; i++) {
    forks.push(new Fork());
}

for (var i = 0; i < N; i++) {
    philosophers.push(new Philosopher(i, forks));
}

var conductor = new Conductor(N - 1);
for (var i = 0; i < N; i++) {
    // philosophers[i].startNaive(10);
    // philosophers[i].startAsym(10);
    philosophers[i].startConductor(10, conductor);
}
