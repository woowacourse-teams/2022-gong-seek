import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const Container = styled.form`
	display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: center;
	gap: ${({ theme }) => theme.size.SIZE_056};

	&:invalid button {
		pointer-events: none;
		background-color: ${({ theme }) => theme.colors.GRAY_500};
	}
`;

export const VoteDeadlineLabel = styled.label`
	font-size: ${({ theme }) => theme.size.SIZE_014};
`;

export const VoteDeadlineInputBox = styled.div`
	width: 70%;

	display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: center;

	gap: ${({ theme }) => theme.size.SIZE_004};
`;

export const DeadlineInput = styled.input`
	width: 100%;

	${({ theme }) => css`
		border-radius: ${theme.size.SIZE_010};
		padding: ${theme.size.SIZE_006} ${theme.size.SIZE_004};
		border-color: ${theme.colors.PURPLE_500};
		color: ${theme.colors.BLACK_400};
		font-size: ${theme.size.SIZE_012};

		&::-webkit-datetime-edit-text {
			color: ${theme.colors.BLUE_500};
		}
	`}
`;

export const ValidateMessage = styled.span`
	margin-top: ${({ theme }) => theme.size.SIZE_012};
	margin-right: auto;
	line-height: 140%;
`;

export const SubmitButton = styled.button`
	width: 70%;
	height: fit-content;

	border-color: transparent;
	cursor: pointer;

	${({ theme }) => css`
		border-radius: ${theme.size.SIZE_010};

		font-size: ${theme.size.SIZE_014};
		color: ${theme.colors.WHITE};
		background-color: ${theme.colors.PURPLE_500};

		padding: ${theme.size.SIZE_004};
		margin-top: ${theme.size.SIZE_050};

		&:hover,
		&:active {
			background-color: ${theme.colors.PURPLE_400};
		}

		@media (min-width: ${theme.breakpoints.DESKTOP_LARGE}) {
			width: ${theme.size.SIZE_100};
			height: ${theme.size.SIZE_040};

			font-size: ${theme.size.SIZE_016};

			margin-left: auto;
		}
	`}
`;
