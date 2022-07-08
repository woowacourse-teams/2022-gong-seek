import styled from '@emotion/styled';
import { IoIosArrowDown } from 'react-icons/io';

export const Container = styled.div`
	width: 100%;
	height: 100%;
	display: flex;
	justify-content: center;
	align-items: center;
	flex-direction: column;
`;

export const Content = styled.div`
	width: 100%;
	margin-top: ${({ theme }) => theme.size.SIZE_028};
`;

export const SelectorBox = styled.div`
	width: 90%;
	display: flex;
	flex-direction: column;
	gap: ${({ theme }) => theme.size.SIZE_010};
`;

export const TitleInput = styled.input`
	width: 100%;
	padding: 0.6rem 0.8rem;
	font-size: 0.8rem;
	border-style: none;
	background-color: transparent;
	&:focus {
		outline: none;
	}
`;

export const HashTagInput = styled.input`
	width: 100%;
	padding: 0.6rem 0.8rem;
	font-size: 0.8rem;
	border-style: none;
	background-color: transparent;
	&:focus {
		outline: none;
	}
`;

export const CategorySelectorBox = styled.div`
	position: relative;
	display: flex;
	align-items: center;
	width: 100%;
`;

export const SelectorButton = styled(IoIosArrowDown)`
	position: absolute;
	border: none;
	font-size: 0.8rem;
	font-size: ${({ theme }) => theme.size.SIZE_018};
	color: ${({ theme }) => theme.colors.PURPLE_500};
	right: ${({ theme }) => theme.size.SIZE_004};
	pointer-events: none;
`;

export const CategorySelector = styled.select`
	width: 100%;
	padding: 0.6rem 0.8rem;
	font-size: 0.8rem;
	border-color: transparent;
	border-radius: ${({ theme }) => theme.size.SIZE_010};
	background-color: transparent;
	-webkit-appearance: none;
	-moz-appearance: none;
	appearance: none;
	&:focus {
		outline: none;
	}

	&:invalid {
		color: rgb(117, 117, 117);
	}
`;

export const SubmitButton = styled.button`
	color: ${({ theme }) => theme.colors.WHITE};
	background-color: ${({ theme }) => theme.colors.PURPLE_500};
	width: 90%;
	font-size: 0.8rem;
	padding: ${({ theme }) => theme.size.SIZE_004};
	border-radius: ${({ theme }) => theme.size.SIZE_010};
	border-color: transparent;
	cursor: pointer;
	margin-top: ${({ theme }) => theme.size.SIZE_020};

	&:hover,
	&:active {
		background-color: ${({ theme }) => theme.colors.PURPLE_400};
	}
`;
