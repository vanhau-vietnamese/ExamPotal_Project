import { Outlet } from "react-router-dom";

function AuthLayout() {
	return (
		<div className="min-h-screen app-wrapper">
			<div className="app-main font-landing">
				<div className="app-content">
					<div className="items-center app-content--inner">
						<div className="flex items-center flex-grow w-full">
							<div className="relative z-10 w-full">
								<div className="w-full px-[20px] mx-auto md:max-w-[1140px]">
									<Outlet />
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	);
}

export default AuthLayout;
