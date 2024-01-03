import { PropsWithChildren } from 'react';

const ListItem = ({ children }: PropsWithChildren) => {
  console.log('children');
  return <li>{children}</li>;
};
export default ListItem;
