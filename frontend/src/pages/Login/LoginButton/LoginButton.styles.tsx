import { LOGIN_TYPE } from '@/constants/loginType';
import { LoginButtonProps } from '@/pages/Login/LoginButton/LoginButton';
import styled from '@emotion/styled';

export const Container = styled.button<Pick<LoginButtonProps, 'loginType'>>`
	display: flex;

	justify-content: center;
	align-items: center;
	gap: 1rem;

	width: 100%;
	height: fit-content;

	border: none;
	border-radius: ${({ theme }) => theme.size.SIZE_010};

	background-color: ${({ loginType }) => LOGIN_TYPE[loginType].color};

	padding: ${({ theme }) => theme.size.SIZE_004};

	cursor: pointer;

	&:hover,
	&active {
		background-color: ${({ theme }) => theme.colors.BLACK_400};
	}
`;

export const IconBox = styled.div`
	font-size: ${({ theme }) => theme.size.SIZE_022};

	color: white;

	margin-top: ${({ theme }) => theme.size.SIZE_002};
`;

export const ContentBox = styled.div<Pick<LoginButtonProps, 'loginType'>>`
	font-size: ${({ theme }) => theme.size.SIZE_014};

	color: ${({ loginType }) => LOGIN_TYPE[loginType].fontcolor};
`;
