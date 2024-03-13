import { useState } from 'react';
import Icons from '~/assets/icons';
import images from '~/assets/images';
import { Button } from '~/components';

function Header() {
  const [openMenu, setOpenMenu] = useState(false);
  return (
    <div className="fixed w-[calc(100%-280px)] h-[64px] flex items-center py-1 backdrop-blur-[6px] ml-[280px] z-0 bg-[#e4e6e8] bg-opacity-15">
      <div className="flex items-center justify-between w-full px-4">
        <div className="flex items-center gap-4 text-icon">
          <Icons.Search />
        </div>
        <div className="relative px-3 py-1 ml-3 rounded flex items-center gap-4 text-icon">
          <Icons.Notification />
          <Button
            className={`flex items-center p-0 text-left cursor-pointer hover:text-primary ${
              openMenu ? 'text-primary' : 'text-icon'
            }`}
            onClick={() => setOpenMenu(!openMenu)}
          >
            <div className="hidden pr-2 text-right md:block text-sm">
              <p className="font-bold">{'FULL_NAME'}</p>
              <span className="font-semibold text-icon opacity-70 text-xs">{'Admin'}</span>
            </div>
            <div className="relative p-1 border border-primary rounded-full">
              <div className="rounded-xl overflow-hidden w-[32px] h-[32px] ">
                <img src={images.logo} alt="avatar" className="object-cover" />
              </div>
            </div>
          </Button>
        </div>
      </div>
    </div>
  );
}

export default Header;
