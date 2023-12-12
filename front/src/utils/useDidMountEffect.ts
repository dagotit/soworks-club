import { useEffect, useRef } from 'react';

const useDidMountEffect = (func: Function, deps: any) => {
  const didMount = useRef(false);

  /**
   * @function
   * useEffect 최초 실행 금지
   */
  useEffect(() => {
    if (didMount.current) func();
    else didMount.current = true;
  }, deps);
};

export default useDidMountEffect;
