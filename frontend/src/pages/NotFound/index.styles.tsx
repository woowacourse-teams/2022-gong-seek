import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const Container = styled.div`
	display: flex;

	flex-direction: column;
	align-items: center;
	justify-content: center;

	width: 100%;
	height: 68vh;
`;

export const ErrorContent = styled.p`
	font-size: ${({ theme }) => theme.size.SIZE_020};
	font-weight: 400;
	line-height: ${({ theme }) => theme.size.SIZE_040};
	text-align: center;

	margin-top: ${({ theme }) => theme.size.SIZE_056};
`;

export const LogoImage = styled.img`
	width: ${({ theme }) => theme.size.SIZE_090};
	height: ${({ theme }) => theme.size.SIZE_090};
`;

export const NavigateLink = styled.div`
	font-weight: 600;
	cursor: pointer;
	${({ theme }) => css`
		border: ${theme.size.SIZE_003} solid ${theme.colors.PURPLE_500};
		padding: ${theme.size.SIZE_010} ${theme.size.SIZE_024};
		margin-top: ${theme.size.SIZE_030};

		&:hover,
		&:active {
			color: ${theme.colors.WHITE};
			background-color: ${theme.colors.PURPLE_500};
		}
	`}
`;
