import styled from '@emotion/styled';

export const Container = styled.div`
	display: flex;

	justify-content: center;
	align-items: center;

	width: 100%;
`;

export const InquireButton = styled.button`
	width: 100%;
	max-width: ${({ theme }) => theme.size.SIZE_260};
	height: fit-content;

	border: none;
	border-radius: ${({ theme }) => theme.size.SIZE_010};

	color: white;
	background-color: ${({ theme }) => theme.colors.PURPLE_500};

	padding: ${({ theme }) => theme.size.SIZE_006};

	cursor: pointer;

	&:hover,
	&active {
		background-color: ${({ theme }) => theme.colors.PURPLE_400};
	}
`;
