import { LOGIN_TYPE } from '@/constants/loginType';
import * as S from '@/pages/Login/LoginButton/LoginButton.styles';

export interface LoginButtonProps {
	loginType: keyof typeof LOGIN_TYPE;
	onClick: (e: React.MouseEvent) => void;
	children: React.ReactNode;
}

const LoginButton = ({ loginType, children, onClick }: LoginButtonProps) => (
	<S.Container onClick={onClick} loginType={loginType}>
		<S.IconBox>{LOGIN_TYPE[loginType].icon}</S.IconBox>
		<S.ContentBox loginType={loginType}>{children}</S.ContentBox>
	</S.Container>
);

export default LoginButton;
