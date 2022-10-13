import { AiOutlineDown } from 'react-icons/ai';
import { Link } from 'react-router-dom';

import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const Container = styled.div`
	display: flex;

	flex-direction: column;
	justify-content: center;
	align-items: center;

	width: 100%;
	min-height: 100%;
	max-width: 900px;
	margin: 0 auto;
	margin-bottom: ${({ theme }) => theme.size.SIZE_040};
`;

export const Content = styled.div`
	width: 100%;
	margin-top: ${({ theme }) => theme.size.SIZE_028};

	@media (min-width: ${({ theme }) => theme.breakpoints.DESKTOP_LARGE}) {
		width: 100%;
	}
`;

export const SelectorBox = styled.div`
	display: flex;

	flex-direction: column;
	gap: ${({ theme }) => theme.size.SIZE_010};

	width: 95%;

	@media (min-width: ${({ theme }) => theme.breakpoints.DESKTOP_LARGE}) {
		width: 100%;
	}
`;

export const TitleInput = styled.input`
	width: 100%;

	border-style: none;

	background-color: transparent;

	&:focus {
		outline: none;
	}

	${({ theme }) => css`
		font-size: ${theme.size.SIZE_014};
		padding: ${theme.size.SIZE_010} ${theme.size.SIZE_014};

		@media (min-width: ${theme.breakpoints.DESKTOP_LARGE}) {
			font-size: ${theme.size.SIZE_020};
			padding: ${theme.size.SIZE_008} ${theme.size.SIZE_010};
		}
	`}
`;

export const TitleInputErrorMsgBox = styled.div`
	color: ${({ theme }) => theme.colors.RED_500};
	font-size: ${({ theme }) => theme.size.SIZE_012};
`;

export const HashTagInput = styled.input`
	width: 100%;

	border-style: none;

	font-size: 0.8rem;

	background-color: transparent;

	padding: 0.6rem 0.8rem;

	&:focus {
		outline: none;
	}
`;

export const OptionBox = styled.div`
	display: flex;

	flex-direction: column;
	gap: ${({ theme }) => theme.size.SIZE_010};

	@media (min-width: ${({ theme }) => theme.breakpoints.DESKTOP_LARGE}) {
		flex-direction: row-reverse;
	}
`;

export const CategorySelectorBox = styled.div`
	display: flex;

	align-items: center;

	width: 100%;
`;

export const SelectorButton = styled(AiOutlineDown)`
	position: relative;
	pointer-events: none;
	border: none;

	${({ theme }) => css`
		right: ${theme.size.SIZE_004};

		font-size: ${theme.size.SIZE_018};
		color: ${theme.colors.PURPLE_500};

		z-index: ${theme.zIndex.SELECTOR_BUTTON};
	`}
`;

export const CategorySelector = styled.select`
	width: 100%;

	border-color: transparent;
	background-color: transparent;

	appearance: none;
	-webkit-appearance: none;
	-moz-appearance: none;

	&:focus {
		outline: none;
	}

	${({ theme }) => css`
		border-radius: ${theme.size.SIZE_010};

		font-size: ${theme.size.SIZE_014};

		padding: ${theme.size.SIZE_010} ${theme.size.SIZE_014};

		&:invalid {
			color: ${theme.colors.BLACK_300};
		}
	`}
`;

export const SubmitButton = styled.button`
	width: 90%;
	height: fit-content;

	border-color: transparent;
	cursor: pointer;

	${({ theme }) => css`
		border-radius: ${theme.size.SIZE_010};

		font-size: ${theme.size.SIZE_014};

		color: ${theme.colors.WHITE};
		background-color: ${theme.colors.PURPLE_500};

		padding: ${theme.size.SIZE_004};

		&:hover,
		&:active {
			background-color: ${theme.colors.PURPLE_400};
		}

		@media (min-width: ${theme.breakpoints.DESKTOP_LARGE}) {
			width: ${theme.size.SIZE_100};
			height: ${theme.size.SIZE_040};

			font-size: ${theme.size.SIZE_016};
		}
	`}
`;

export const UpdateSubmitBox = styled.div`
	display: flex;

	width: 100%;
	justify-content: center;

	@media (min-width: ${({ theme }) => theme.breakpoints.DESKTOP_LARGE}) {
		justify-content: flex-end;
	}
`;

export const UpdateSubmitButton = styled.button`
	width: 90%;
	height: fit-content;

	border-color: transparent;
	cursor: pointer;

	${({ theme }) => css`
		border-radius: ${theme.size.SIZE_010};

		font-size: ${theme.size.SIZE_014};

		color: ${theme.colors.WHITE};
		background-color: ${theme.colors.PURPLE_500};

		padding: ${theme.size.SIZE_004};
		margin-top: ${theme.size.SIZE_020};

		&:hover,
		&:active {
			background-color: ${theme.colors.PURPLE_400};
		}

		@media (min-width: ${theme.breakpoints.DESKTOP_LARGE}) {
			width: ${theme.size.SIZE_100};
			height: ${theme.size.SIZE_040};

			font-size: ${theme.size.SIZE_016};
		}
	`}
`;

export const LinkButton = styled(Link)`
	width: 88%;

	border-color: transparent;
	text-align: center;
	text-decoration: none;

	cursor: pointer;

	&:hover,
	&:active {
		filter: brightness(1.01);
	}

	${({ theme }) => css`
		border-radius: ${theme.size.SIZE_010};

		font-size: ${theme.size.SIZE_014};

		color: ${theme.colors.WHITE};
		background-color: ${theme.colors.GREEN_500};

		padding: ${theme.size.SIZE_004};
		margin-top: ${theme.size.SIZE_020};
	`}
`;

export const SubmitBox = styled.div`
	display: flex;

	align-items: center;
	justify-content: end;

	width: 85%;

	${({ theme }) => css`
		gap: ${theme.size.SIZE_022};
		margin-top: ${theme.size.SIZE_050};
		@media (min-width: ${theme.breakpoints.DESKTOP_LARGE}) {
			margin-left: auto;
			width: min-content;
		}
	`}
`;

export const TemporaryStoreButtonBox = styled.div`
	width: 90%;
	display: flex;

	justify-content: flex-end;

	@media (min-width: ${({ theme }) => theme.breakpoints.DESKTOP_SMALL}) {
		width: 100%;
		margin-top: ${({ theme }) => theme.size.SIZE_020};
	}
`;

export const TemporaryStoreButton = styled.button`
	width: fit-content;
	background-color: transparent;
	border: none;

	${({ theme }) => css`
		padding: ${theme.size.SIZE_004};

		border-radius: ${theme.size.SIZE_004};

		font-size: ${theme.size.SIZE_014};

		&:hover,
		&:active {
			background-color: ${theme.colors.PURPLE_500};
			color: ${theme.colors.WHITE};
		}
	`}
`;
