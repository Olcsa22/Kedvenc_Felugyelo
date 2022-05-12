
export class Hamster{

    public id?: number;
    public name?: string;
    public lastFeeding?: Date;
    public feedingInterval ?: number;
    public owner ?: number;
    public toothwearerChanged?: Date;
    public needs?: String[];
    public lastCleaning ?: Date;
    public url?:String;
  

    constructor(id?: number, name?: string, lastFeeding?: Date, feedingInterval ?: number, owner ?: number, 
        toothwearerChanged ?: Date, lastCleaning?:Date){
          this.id=id;
          this.name=name;
          this.lastFeeding=lastFeeding;
          this.feedingInterval=feedingInterval;
          this.owner=owner;
          this.toothwearerChanged=toothwearerChanged;
          this.lastCleaning=lastCleaning;
    }
  
  }
  