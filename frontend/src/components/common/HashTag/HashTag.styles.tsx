import { theme } from '@/styles/Theme';
import styled from '@emotion/styled';

export const Container = styled.div`
	display: flex;
	flex-direction: column;
	width: 100%;

	gap: ${({ theme }) => theme.size.SIZE_010};
`;

export const HashTagItemBox = styled.div`
	display: flex;

	width: 100%;
	height: fit-content;

	flex-wrap: wrap;

	gap: ${({ theme }) => theme.size.SIZE_004};
	padding: ${({ theme }) => theme.size.SIZE_002};
`;

export const HastTagItem = styled.div`
	width: fit-content;
	height: fit-content;

	font-size: ${({ theme }) => theme.size.SIZE_014};

	padding: ${({ theme }) => theme.size.SIZE_004};

	border-radius: ${({ theme }) => theme.size.SIZE_004};
	background-color: ${({ theme }) => theme.colors.PURPLE_100};
`;

export const HashTagForm = styled.form`
	display: flex;
	flex-direction: column;

	padding: ${({ theme }) => theme.size.SIZE_004};

	gap: ${({ theme }) => theme.size.SIZE_004};
`;

export const HastTagInput = styled.input`
	width: 100%;

	border: none;
	background-color: transparent;

	padding: 0.6rem 0.8rem;

	font-size: 0.8rem;

	&:focus {
		outline: none;
	}

	&::placeholder {
		font-size: ${({ theme }) => theme.size.SIZE_012};
	}
`;

export const ErrorMessage = styled.div`
	font-size: ${({ theme }) => theme.size.SIZE_012};
	color: ${({ theme }) => theme.colors.RED_500};
`;
