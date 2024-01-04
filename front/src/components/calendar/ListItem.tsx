import { PropsWithChildren } from 'react';

const ListItem = ({ children }: PropsWithChildren) => {
  console.log('children', children);
  return <li>{children}</li>;
};
export default ListItem;
