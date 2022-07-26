import { IoIosArrowDown } from 'react-icons/io';
import { Link } from 'react-router-dom';

import styled from '@emotion/styled';

export const Container = styled.div`
	display: flex;

	flex-direction: column;
	justify-content: center;
	align-items: center;

	width: 100%;
	height: 100%;
`;

export const Content = styled.div`
	width: 100%;

	margin-top: ${({ theme }) => theme.size.SIZE_028};
`;

export const SelectorBox = styled.div`
	display: flex;

	flex-direction: column;
	gap: ${({ theme }) => theme.size.SIZE_010};

	width: 90%;
`;

export const TitleInput = styled.input`
	width: 100%;

	border-style: none;

	font-size: 0.8rem;

	background-color: transparent;

	padding: 0.6rem 0.8rem;

	&:focus {
		outline: none;
	}
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

export const CategorySelectorBox = styled.div`
	display: flex;
	position: relative;

	align-items: center;

	width: 100%;
`;

export const SelectorButton = styled(IoIosArrowDown)`
	position: absolute;

	right: ${({ theme }) => theme.size.SIZE_004};

	border: none;

	font-size: ${({ theme }) => theme.size.SIZE_018};

	color: ${({ theme }) => theme.colors.PURPLE_500};

	pointer-events: none;
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
