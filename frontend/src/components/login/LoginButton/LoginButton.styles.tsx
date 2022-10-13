import { LoginButtonProps } from '@/components/login/LoginButton/LoginButton';
import { LOGIN_TYPE } from '@/constants/loginType';
import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const Container = styled.button<Pick<LoginButtonProps, 'loginType'>>`
	display: flex;

	justify-content: center;
	align-items: center;

	width: 80%;

	height: fit-content;

	border: none;
	${({ theme, loginType }) => css`
		gap: ${theme.size.SIZE_016}
		max-width: ${theme.size.SIZE_200};
		border-radius: ${theme.size.SIZE_010};

		background-color: ${LOGIN_TYPE[loginType].color};

		padding: ${theme.size.SIZE_004};

		&:hover,
		&active {
			background-color: ${theme.colors.BLACK_400};
		}
	`}

	cursor: pointer;
`;

export const IconBox = styled.div`
	${({ theme }) => css`
		font-size: ${theme.size.SIZE_022};

		color: ${theme.colors.WHITE};
		margin-top: ${theme.size.SIZE_002};
	`}
`;

export const ContentBox = styled.div<Pick<LoginButtonProps, 'loginType'>>`
	font-size: ${({ theme }) => theme.size.SIZE_014};

	color: ${({ loginType }) => LOGIN_TYPE[loginType].fontcolor};
`;
