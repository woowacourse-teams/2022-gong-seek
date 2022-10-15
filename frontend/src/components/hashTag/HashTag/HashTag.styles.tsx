import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const Container = styled.div`
	display: flex;
	flex-direction: column;
	width: 100%;

	gap: ${({ theme }) => theme.size.SIZE_010};
`;

export const HashTagItemBox = styled.div`
	display: flex;

	width: fit-content;
	height: fit-content;

	flex-wrap: wrap;

	${({ theme }) => css`
		gap: ${theme.size.SIZE_004};
		padding: ${theme.size.SIZE_002};
	`}
`;

export const HastTagItem = styled.div`
	width: fit-content;
	height: fit-content;

	${({ theme }) => css`
		font-size: ${theme.size.SIZE_014};
		padding: ${theme.size.SIZE_004};

		border-radius: ${theme.size.SIZE_004};
		background-color: ${theme.colors.PURPLE_100};
	`}
`;

export const HashTagForm = styled.form`
	display: flex;
	flex-direction: column;

	${({ theme }) => css`
		padding: ${theme.size.SIZE_004};
		gap: ${theme.size.SIZE_004};
	`}
`;

export const HastTagInput = styled.input`
	width: 60%;
	overflow: auto;

	border: none;
	background-color: transparent;

	&:focus {
		outline: none;
	}

	${({ theme }) => css`
	padding: ${theme.size.SIZE_010} ${theme.size.SIZE_014}
	font-size: ${theme.size.SIZE_010};

	&::placeholder {
		font-size: ${theme.size.SIZE_012};
	}
	`}
`;

export const ErrorMessage = styled.div`
	${({ theme }) => css`
		font-size: ${theme.size.SIZE_012};
		color: ${theme.colors.RED_500};
	`}
`;
