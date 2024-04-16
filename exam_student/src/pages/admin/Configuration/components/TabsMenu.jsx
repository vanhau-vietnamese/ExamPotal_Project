import { NavLink } from 'react-router-dom';
import { Card } from '~/components';

const TabsMenuData = [
  {
    path: 'account',
    name: 'Tài khoản',
  },
  {
    path: 'category',
    name: 'Danh mục',
  },
];

function TabsMenu() {
  return (
    <Card className="h-fit min-h-36 w-full max-w-60 flex flex-col gap-y-2">
      {TabsMenuData.map((item, index) => (
        <div key={index}>
          <NavLink
            to={item.path}
            className={({ isActive }) =>
              `flex items-center px-4 py-2 rounded gap-x-2 text-sm transition-all text-icon ${
                isActive ? 'bg-primary text-primary bg-opacity-20' : 'bg-transparent'
              } hover:bg-primary text-sm hover:bg-opacity-20 hover:text-emerald-500`
            }
          >
            <span className="font-medium">{item.name}</span>
          </NavLink>
        </div>
      ))}
    </Card>
  );
}

export default TabsMenu;
