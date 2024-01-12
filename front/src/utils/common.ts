export function isEmptyObj(obj: object): boolean {
  if (obj.constructor === Object && Object.keys(obj).length === 0) {
    return true;
  }

  return false;
}
