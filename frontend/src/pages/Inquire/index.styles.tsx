import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const Container = styled.div`
	display: flex;

	justify-content: center;
	align-items: center;

	width: 100%;
	height: 60vh;
`;

export const InquireButton = styled.button`
	width: 100%;

	height: fit-content;

	border: none;
	cursor: pointer;

	${({ theme }) => css`
		max-width: ${theme.size.SIZE_260};
		border-radius: ${theme.size.SIZE_010};

		color: ${theme.colors.WHITE};
		background-color: ${theme.colors.PURPLE_500};

		padding: ${theme.size.SIZE_006};

		&:hover,
		&active {
			background-color: ${theme.colors.PURPLE_400};
		}
	`}
`;
