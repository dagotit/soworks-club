export function isEmptyObj(obj: any): boolean {
  // @ts-ignore
  if (obj === undefined || obj === 'undefined' || obj === null || obj === '') {
    return true
  }
  if (obj.constructor === Object && Object.keys(obj).length === 0) {
    return true;
  }

  return false;
}

export function isEmptyArr(arr: Array<any>): boolean {
  // @ts-ignore
  if (arr === undefined || arr === 'undefined' || arr === null || arr === '') {
    return true
  }
  if (Array.isArray(arr) && arr.length === 0) {
    return true;
  }

  return false;
}
