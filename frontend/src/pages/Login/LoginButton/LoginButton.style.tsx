import styled from '@emotion/styled';
import { LOGIN_TYPE } from '@/constants/loginType';
import { LoginButtonProps } from '@/pages/Login/LoginButton/LoginButton';

export const Container = styled.button<Pick<LoginButtonProps, 'loginType'>>`
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

export const IconBox = styled.div`
	color: white;
	font-size: ${({ theme }) => theme.fonts.SIZE_022};
	margin-top: ${({ theme }) => theme.fonts.SIZE_002};
`;

export const ContentBox = styled.div<Pick<LoginButtonProps, 'loginType'>>`
	font-size: ${({ theme }) => theme.fonts.SIZE_014};
	color: ${({ loginType }) => LOGIN_TYPE[loginType].fontcolor};
`;
