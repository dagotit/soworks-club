export function isEmptyObj(obj: object): boolean {
  if (obj.constructor === Object && Object.keys(obj).length === 0) {
    return true;
  }

  return false;
}

export function isEmptyArr(arr: Array<any>): boolean {
  if (Array.isArray(arr) && arr.length === 0) {
    return true;
  }

  return false;
}
