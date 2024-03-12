import { zodResolver } from '@hookform/resolvers/zod';
import { useForm } from 'react-hook-form';
import { Link } from 'react-router-dom';
import Icons from '~/assets/icons';
import images from '~/assets/images';
import { Button, FormInput } from '~/components';
import { useAuth } from '~/hooks';
import { router } from '~/routes';
import { FormSignInInput } from '~/validations';

function SignInPage() {
  const { signInWithSocial } = useAuth();

  const {
    control,
    formState: { errors },
    handleSubmit,
  } = useForm({ resolver: zodResolver(FormSignInInput), mode: 'onBlur' });

  const onSubmit = handleSubmit((data) => console.log(data));

  // const handleLoginWithSocial = () => {
  //   console.log('Login with social');
  // };

  return (
    <div className="flex items-center justify-center w-full h-full">
      <div className="items-center flex-grow-0 flex-shrink-0 hidden px-3 md:flex w-[50%]">
        <img
          src={images.dashboard}
          alt="..."
          className="block object-cover w-full h-full mx-auto"
        />
      </div>
      <div className="flex-1 md:max-w-[50%]">
        <div className="px-5 mx-auto">
          <div className="w-full ">
            <p className="my-3 text-2xl font-bold text-center text-icon">Đăng nhập</p>
            <form onSubmit={onSubmit}>
              <FormInput
                control={control}
                title="Địa chỉ Email"
                name="email"
                placeholder="Email"
                required
                type="text"
                icon={<Icons.Email />}
                error={errors.email?.message}
              />
              <FormInput
                control={control}
                title="Mật khẩu"
                name="password"
                placeholder="Mật khẩu"
                required
                type={'password'}
                icon={<Icons.Key />}
                error={errors.password?.message}
              />
              <div className="text-left">
                <Button
                  type="submit"
                  className="w-full px-5 py-2 text-[16px] text-white bg-primary shadow-success hover:shadow-success_hover"
                >
                  Đăng nhập
                </Button>
              </div>
            </form>
            <div className="text-[14px] font-semibold text-center m-[12px_auto] text-icon">
              <span>Hoặc đăng nhập bằng</span>
            </div>
            <div className="flex items-center gap-8 w-full">
              <Button
                className="flex items-center justify-center p-2 flex-1 gap-2 text-icon bg-strike shadow-sidebar hover:bg-[#c0c1cd] text-[15px]"
                onClick={() => signInWithSocial('google')}
              >
                <img src={images.google} alt="google_logo" />
                <p>Google</p>
              </Button>
              <Button
                className="flex items-center justify-center p-2 flex-1 gap-2 text-icon bg-strike shadow-sidebar hover:bg-[#c0c1cd] text-[15px]"
                onClick={() => signInWithSocial('facebook')}
              >
                <img src={images.facebook} alt="facebook_logo" />
                <p>Facebook</p>
              </Button>
            </div>
            <div className="flex items-center justify-center">
              <p className="mt-4 font-semibold text-icon text-[15px]">
                Bạn chưa có tài khoản?{' '}
                <Link to={router.signUp} className="text-primary font-bold underline">
                  Đăng ký tại đây
                </Link>
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default SignInPage;
