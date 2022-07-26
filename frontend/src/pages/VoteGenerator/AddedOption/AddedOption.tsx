import styled from '@emotion/styled';

const AddedOption = styled.div`
	width: 70%;
	height: ${({ theme }) => theme.size.SIZE_040};

	border-radius: ${({ theme }) => theme.size.SIZE_006};

	font-size: ${({ theme }) => theme.size.SIZE_014};
	line-height: ${({ theme }) => theme.size.SIZE_040};

	box-shadow: 1px 1px 4px ${({ theme }) => theme.boxShadows.primary};

	padding-left: ${({ theme }) => theme.size.SIZE_012};
`;

export default AddedOption;
