import { AiOutlineDown } from 'react-icons/ai';
import { Link } from 'react-router-dom';

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

	font-size: 0.8rem;

	background-color: transparent;

	padding: 0.6rem 0.8rem;

	@media (min-width: ${({ theme }) => theme.breakpoints.DESKTOP_LARGE}) {
		font-size: ${({ theme }) => theme.size.SIZE_020};
		padding: ${({ theme }) => theme.size.SIZE_008} ${({ theme }) => theme.size.SIZE_010};
	}

	&:focus {
		outline: none;
	}
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

	right: ${({ theme }) => theme.size.SIZE_004};

	border: none;

	font-size: ${({ theme }) => theme.size.SIZE_018};

	color: ${({ theme }) => theme.colors.PURPLE_500};

	pointer-events: none;

	z-index: -100;
`;

export const CategorySelector = styled.select`
	width: 100%;

	border-color: transparent;
	border-radius: ${({ theme }) => theme.size.SIZE_010};

	font-size: 0.8rem;

	background-color: transparent;

	padding: 0.6rem 0.8rem;

	appearance: none;
	-webkit-appearance: none;
	-moz-appearance: none;

	&:focus {
		outline: none;
	}

	&:invalid {
		color: rgb(117, 117, 117);
	}
`;

export const SubmitButton = styled.button`
	width: 90%;
	height: fit-content;

	border-radius: ${({ theme }) => theme.size.SIZE_010};
	border-color: transparent;

	font-size: 0.8rem;

	color: ${({ theme }) => theme.colors.WHITE};
	background-color: ${({ theme }) => theme.colors.PURPLE_500};

	padding: ${({ theme }) => theme.size.SIZE_004};

	cursor: pointer;

	&:hover,
	&:active {
		background-color: ${({ theme }) => theme.colors.PURPLE_400};
	}

	@media (min-width: ${({ theme }) => theme.breakpoints.DESKTOP_LARGE}) {
		width: ${({ theme }) => theme.size.SIZE_100};
		height: ${({ theme }) => theme.size.SIZE_040};

		font-size: ${({ theme }) => theme.size.SIZE_016};
	}
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

	border-radius: ${({ theme }) => theme.size.SIZE_010};
	border-color: transparent;

	font-size: 0.8rem;

	color: ${({ theme }) => theme.colors.WHITE};
	background-color: ${({ theme }) => theme.colors.PURPLE_500};

	padding: ${({ theme }) => theme.size.SIZE_004};
	margin-top: ${({ theme }) => theme.size.SIZE_020};

	cursor: pointer;

	&:hover,
	&:active {
		background-color: ${({ theme }) => theme.colors.PURPLE_400};
	}

	@media (min-width: ${({ theme }) => theme.breakpoints.DESKTOP_LARGE}) {
		width: ${({ theme }) => theme.size.SIZE_100};
		height: ${({ theme }) => theme.size.SIZE_040};

		font-size: ${({ theme }) => theme.size.SIZE_016};
	}
`;

export const LinkButton = styled(Link)`
	width: 88%;

	border-radius: ${({ theme }) => theme.size.SIZE_010};
	border-color: transparent;

	font-size: 0.8rem;
	text-align: center;
	text-decoration: none;

	color: ${({ theme }) => theme.colors.WHITE};
	background-color: ${({ theme }) => theme.colors.GREEN_500};

	padding: ${({ theme }) => theme.size.SIZE_004};
	margin-top: ${({ theme }) => theme.size.SIZE_020};

	cursor: pointer;

	&:hover,
	&:active {
		filter: brightness(1.01);
	}
`;

export const SubmitBox = styled.div`
	display: flex;
	gap: ${({ theme }) => theme.size.SIZE_022};

	align-items: center;
	justify-content: end;
	margin-top: ${({ theme }) => theme.size.SIZE_050};
	width: 85%;

	@media (min-width: ${({ theme }) => theme.breakpoints.DESKTOP_LARGE}) {
		margin-left: auto;
		width: min-content;
	}
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
	padding: ${({ theme }) => theme.size.SIZE_004};

	border-radius: ${({ theme }) => theme.size.SIZE_004};
	background-color: transparent;
	border: none;

	font-size: ${({ theme }) => theme.size.SIZE_014};

	&:hover,
	&:active {
		background-color: ${({ theme }) => theme.colors.PURPLE_500};
		color: ${({ theme }) => theme.colors.WHITE};
	}
`;
