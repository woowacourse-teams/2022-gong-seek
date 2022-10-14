import { theme } from '@/styles/Theme';
import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const Container = styled.nav`
	display: flex;
	width: 100%;
	height: fit-content;

	gap: ${({ theme }) => theme.size.SIZE_001};

	margin-top: ${({ theme }) => theme.size.SIZE_040};
`;

export const Tab = styled.button<{ isClicked: boolean }>`
	height: fit-content;
	text-align: center;

	${({ theme, isClicked }) => css`
		width: ${theme.size.SIZE_140};

		padding: ${theme.size.SIZE_006};

		background-color: ${isClicked ? `${theme.colors.PURPLE_500}` : `${theme.colors.WHITE}`};
		color: ${isClicked ? `${theme.colors.WHITE}` : `${theme.colors.PURPLE_500}`};
		border-radius: ${theme.size.SIZE_004} ${theme.size.SIZE_004} 0 0;
		border: ${theme.size.SIZE_001} solid ${theme.colors.GRAY_500};

		&:hover,
		&:active {
			cursor: pointer;
			background-color: ${theme.colors.PURPLE_500};
			color: ${theme.colors.WHITE};
		}
	`}
`;
