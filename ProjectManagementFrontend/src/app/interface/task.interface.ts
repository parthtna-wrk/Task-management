interface ITask {
    id?: string;
    title: string;
    type: string;
    dueDate: Date;
    description: string;
  }
  interface ITaskTypeOption {
    type: string;
  }
  
  interface ITypePercentage {
    count: number;
    type: string;
  }
  
  interface IUser{
    firstName:string ;
    lastName:string;
    email:string;
    userName:string;
    id:number;
  }
  export { ITask, ITaskTypeOption, ITypePercentage ,IUser};