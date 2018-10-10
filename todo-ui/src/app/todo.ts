export class Todo {

    id: number;
    text: string;
    done: boolean;
  
    constructor(values: Object = {}) {
      Object.assign(this, values);
    }
  
  }
  