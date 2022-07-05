import { LOGIN_TYPE } from '@/constants/loginType';

import styled from '@emotion/styled';

export interface LoginButtonProps {
	loginType: keyof typeof LOGIN_TYPE;
	onClick: (e: React.MouseEvent) => void;
	children: React.ReactNode;
}

const Container = styled.button<Pick<LoginButtonProps, 'loginType'>>`
	width: 100%;
	height: fit-content;
	padding: ${({ theme }) => theme.fonts.SIZE_004};
	display: flex;
	justify-content: center;
	align-items: center;
	gap: 1rem;
	border: none;
	border-radius: ${({ theme }) => theme.fonts.SIZE_010};
	background-color: ${({ loginType }) => LOGIN_TYPE[loginType].color};

	cursor: pointer;

	&:hover,
	&active {
		background-color: ${({ theme }) => theme.colors.BLACK_400};
	}
`;

const IconBox = styled.div`
	color: white;
	font-size: ${({ theme }) => theme.fonts.SIZE_022};
	margin-top: ${({ theme }) => theme.fonts.SIZE_002};
`;

const ContentBox = styled.div<Pick<LoginButtonProps, 'loginType'>>`
	font-size: ${({ theme }) => theme.fonts.SIZE_014};
	color: ${({ loginType }) => LOGIN_TYPE[loginType].fontcolor};
`;

const LoginButton = ({ loginType, children, onClick }: LoginButtonProps) => (
	<Container onClick={onClick} loginType={loginType}>
		<IconBox>{LOGIN_TYPE[loginType].icon}</IconBox>
		<ContentBox loginType={loginType}>{children}</ContentBox>
	</Container>
);

export default LoginButton;
